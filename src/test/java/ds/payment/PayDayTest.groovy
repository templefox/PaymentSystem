package ds.payment

import ds.payment.database.DB
import ds.payment.transaction.AddHourlyEmployeeTransaction
import ds.payment.transaction.AddSalariedEmployeeTransaction
import ds.payment.transaction.PaydayTransaction
import ds.payment.transaction.TimeCardTransaction
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

/**
 * Created by Administrator on 2015/9/13.
 */
class PayDayTest extends Specification {
    @Shared
    DB db

    def setupSpec() {
        ApplicationContext context = SpringContext.instance
        db = context.getBean(DB)
    }

    def "PaySingleSalariedEmployeeTest"() {
        setup:
        Calendar c = Calendar.instance
        c.setTime(payDate)
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE))
        payDate = c.getTime()
        when:
        new AddSalariedEmployeeTransaction(empid, name, address, salary).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()
        def pc = pt.getPayCheck(empid)
        then:
        pc
        pc.getPayDate() == payDate
        (salary as Number) closeTo(salary, 0.001)
        "Hold" == pc.getField("Disposition")
        (salary as Number) closeTo(salary, 0.001)

        where:
        empid | name     | address  | salary | memberId | fee | payDate
        3     | "Jason"  | "home"   | 120.00 | 100      | 0.2 | new Date().parse('yyyy/MM/dd', '2015/9/30')
        4     | "Homers" | "office" | 230.43 | 200      | 0.3 | new Date().parse('yyyy/MM/dd', '1973/07/09')
    }

    def "NotGoodDayToPayTest"() {
        setup:
        Calendar c = Calendar.instance
        c.setTime(payDate)
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE) - 3)
        payDate = c.getTime()
        when:
        new AddSalariedEmployeeTransaction(empid, name, address, salary).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()
        def pc = pt.getPayCheck(empid)
        then:
        !pc

        where:
        empid | name     | address  | salary | memberId | fee | payDate
        3     | "Jason"  | "home"   | 120.00 | 100      | 0.2 | new Date().parse('yyyy/MM/dd', '2015/9/30')
        4     | "Homers" | "office" | 230.43 | 200      | 0.3 | new Date().parse('yyyy/MM/dd', '1973/07/09')
    }

    def "TestPaySingleHourlyEmployeeNoTimeCards"() {
        when:
        new AddHourlyEmployeeTransaction(empId, name, address, salary).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()

        then:
        def pc = pt.getPayCheck(empId)
        pc
        pc.getPayPeriodEndDate() == payDate
        pc.grossPay == 0
        pc.getField("Disposition") == "Hold"
        pc.deductions == 0
        pc.netPay == 0

        where:
        empId | name     | address  | salary | memberId | fee | payDate
        143   | "Jason"  | "home"   | 120.00 | 100      | 0.2 | new Date().parse('yyyy/MM/dd', '2015/9/18')
        444   | "Homers" | "office" | 230.43 | 200      | 0.3 | new Date().parse('yyyy/MM/dd', '2015/09/25')
    }

    def "Test...One Time Card"() {
        when:
        new AddHourlyEmployeeTransaction(empId, name, address, salary).execute()
        new TimeCardTransaction(payDate, hours, empId).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()

        then:
        def pc = pt.getPayCheck(empId)
        pc
        pc.getPayPeriodEndDate() == payDate
        (salary * hours) closeTo(pc.grossPay, 0.001)
        pc.deductions == 0
        pc.netPay == pc.grossPay

        where:
        empId | name     | address  | salary | memberId | hours | payDate
        18    | "Jason"  | "home"   | 120.00 | 100      | 2     | new Date().parse('yyyy/MM/dd', '2015/9/18')
        19    | "Homers" | "office" | 230.43 | 200      | 3     | new Date().parse('yyyy/MM/dd', '2015/09/25')
    }

    def "Test..OverTime"() {
        when:
        new AddHourlyEmployeeTransaction(empId, name, address, salary).execute()
        new TimeCardTransaction(payDate, hours, empId).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()

        then:
        def pc = pt.getPayCheck(empId)
        pc
        pc.getPayPeriodEndDate() == payDate
        (salary * ((hours - 8) * 1.5 + 8)) closeTo(pc.grossPay, 0.001)
        pc.deductions == 0
        pc.netPay == pc.grossPay

        where:
        empId | name     | address  | salary | memberId | hours | payDate
        23    | "Jason"  | "home"   | 120.00 | 100      | 9     | new Date().parse('yyyy/MM/dd', '2015/9/18')
        24    | "Homers" | "office" | 230.43 | 200      | 12    | new Date().parse('yyyy/MM/dd', '2015/09/25')
    }

    def "TestPaySingleHourlyEmployeeTwoTimeCards"() {
        when:
        new AddHourlyEmployeeTransaction(empId, name, address, salary).execute()
        new TimeCardTransaction(payDate, hours, empId).execute()
        new TimeCardTransaction(payDate2, hours, empId).execute()

        def pt = new PaydayTransaction(payDate)
        pt.execute()

        then:
        def pc = pt.getPayCheck(empId)
        pc
        pc.getPayPeriodEndDate() == payDate
        (salary * hours * 2) closeTo(pc.grossPay, 0.001)
        pc.deductions == 0
        pc.netPay == pc.grossPay

        where:
        empId | name     | address  | salary | memberId | hours | payDate                                      | payDate2
        33    | "Jason"  | "home"   | 120.00 | 100      | 1     | new Date().parse('yyyy/MM/dd', '2015/9/18')  | new Date().parse('yyyy/MM/dd', '2015/9/17')
        34    | "Homers" | "office" | 230.43 | 200      | 3     | new Date().parse('yyyy/MM/dd', '2015/09/25') | new Date().parse('yyyy/MM/dd', '2015/9/21')
    }
}
