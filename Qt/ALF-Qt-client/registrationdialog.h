#ifndef REGISTRATIONDIALOG_H
#define REGISTRATIONDIALOG_H

#include <QDialog>
#include <QLineEdit>

class RegistrationDialog : public QDialog
{
    Q_OBJECT

public:
    RegistrationDialog(QWidget *parent = nullptr);

private slots:
    void enableRegisterButton();

private:
    QLineEdit *usernameInput;
    QLineEdit *emailInput;
    QLineEdit *passwordInput;
    QLineEdit *confirmPasswordInput;
    QPushButton *registerButton;
};

#endif