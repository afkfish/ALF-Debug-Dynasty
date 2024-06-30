#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include "UserLoginDialog.h"
#include "UserInterface.h"

UserLoginDialog::UserLoginDialog(QWidget *parent) : QDialog(parent) {
    this->setWindowTitle("User Login");

    QVBoxLayout *loginLayout = new QVBoxLayout(this);

    QLabel *usernameLabel = new QLabel("Username:", this);
    loginLayout->addWidget(usernameLabel);

    usernameInput = new QLineEdit(this);
    loginLayout->addWidget(usernameInput);

    QLabel *passwordLabel = new QLabel("Password:", this);
    loginLayout->addWidget(passwordLabel);

    passwordInput = new QLineEdit(this);
    passwordInput->setEchoMode(QLineEdit::Password);
    loginLayout->addWidget(passwordInput);

    loginButton = new QPushButton("Login", this);
    loginLayout->addWidget(loginButton);

    QObject::connect(loginButton, &QPushButton::clicked, this, [&]() {
        UserInterface *userInterface = new UserInterface;
        userInterface->show();
        this->close();
        emit loginSuccessful();
    });
}

void UserLoginDialog::enableLoginButton() {
    // Implement the logic to enable the login button
}
