#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include "adminlogindialog.h"
#include "admininterface.h"

AdminLoginDialog::AdminLoginDialog(QWidget *parent) : QDialog(parent) {
    this->setWindowTitle("Admin Login");

    QVBoxLayout *loginLayout = new QVBoxLayout(this);

    QLabel *usernameLabel = new QLabel("Username:", this);
    loginLayout->addWidget(usernameLabel);

    adminUsernameInput = new QLineEdit(this);
    loginLayout->addWidget(adminUsernameInput);

    QLabel *passwordLabel = new QLabel("Password:", this);
    loginLayout->addWidget(passwordLabel);

    adminPasswordInput = new QLineEdit(this);
    adminPasswordInput->setEchoMode(QLineEdit::Password);
    loginLayout->addWidget(adminPasswordInput);

    adminLoginButton = new QPushButton("Login", this);
    loginLayout->addWidget(adminLoginButton);

    QObject::connect(adminLoginButton, &QPushButton::clicked, this, [&]() {
        AdminInterface *adminInterface = new AdminInterface;
        adminInterface->show();
        this->close();
        emit loginSuccessful();
    });
}

void AdminLoginDialog::enableLoginButton() {
    // Implement the logic to enable the login button
}