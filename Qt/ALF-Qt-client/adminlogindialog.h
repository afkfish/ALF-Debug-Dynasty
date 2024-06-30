#ifndef ADMINLOGINDIALOG_H
#define ADMINLOGINDIALOG_H

#include <QDialog>
#include <QLineEdit>
#include <QPushButton>

class AdminLoginDialog : public QDialog
{
    Q_OBJECT

public:
    explicit AdminLoginDialog(QWidget *parent = nullptr);
signals:
    void loginSuccessful();
private slots:
    void enableLoginButton();

private:
    QLineEdit *adminUsernameInput;
    QLineEdit *adminPasswordInput;
    QPushButton *adminLoginButton;
};

#endif