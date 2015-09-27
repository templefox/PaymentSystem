package ds.payment

import ds.payment.transaction.AddSalariedEmployeeTransaction
import ds.payment.transaction.ChangeMemberTransaction
import ds.payment.transaction.PaydayTransaction
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

/**
 * Created by Administrator on 2015/9/20.
 */
class AffiliationTest extends Specification {
    def "TestSalariedUnionMemberDues"() {
        when:
        new AddSalariedEmployeeTransaction(empId, name, address, salary).execute()
        new ChangeMemberTransaction(empId, memberId, fee).execute()
        def pt = new PaydayTransaction(payDate)
        pt.execute()
        def pc = pt.getPayCheck(empId)


        then:
        pc
        (salary as Number) closeTo(pc.getGrossPay(), 0.001)
        ((salary - fee * 4) as Number) closeTo(pc.getNetPay(), 0.001)
        (fee * 4 as Number) closeTo(pc.deductions, 0.001)
        where:
        empId | name     | address  | salary | memberId | fee | payDate
        13    | "Jason"  | "home"   | 120.00 | 100      | 9.3 | new Date().parse('yyyy/MM/dd', '2015/9/30')
        14    | "Homers" | "office" | 230.43 | 200      | 3.3 | new Date().parse('yyyy/MM/dd', '2015/8/31')
    }
}
