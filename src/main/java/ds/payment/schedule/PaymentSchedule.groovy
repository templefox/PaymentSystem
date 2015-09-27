package ds.payment.schedule

interface PaymentSchedule {
    boolean isPayDay(Date date)

    Date calculatePayPeriodStartDate(Date date)
}
