#include "registrationdialog.h"
#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>

RegistrationDialog::RegistrationDialog(QWidget *parent)
    : QDialog(parent)
{
    QVBoxLayout *layout = new QVBoxLayout(this);

    QLabel *usernameLabel = new QLabel("Username:", this);
    layout->addWidget(usernameLabel);

    usernameInput = new QLineEdit(this);
    layout->addWidget(usernameInput);

    QLabel *emailLabel = new QLabel("Email:", this);
    layout->addWidget(emailLabel);

    emailInput = new QLineEdit(this);
    layout->addWidget(emailInput);

    QLabel *passwordLabel = new QLabel("Password:", this);
    layout->addWidget(passwordLabel);

    passwordInput = new QLineEdit(this);
    passwordInput->setEchoMode(QLineEdit::Password);
    layout->addWidget(passwordInput);

    QLabel *confirmPasswordLabel = new QLabel("Confirm Password:", this);
    layout->addWidget(confirmPasswordLabel);

    confirmPasswordInput = new QLineEdit(this);
    confirmPasswordInput->setEchoMode(QLineEdit::Password);
    layout->addWidget(confirmPasswordInput);

    registerButton = new QPushButton("Register", this);
    registerButton->setEnabled(false);
    layout->addWidget(registerButton);

    connect(usernameInput, &QLineEdit::textChanged, this, &RegistrationDialog::enableRegisterButton);
    connect(emailInput, &QLineEdit::textChanged, this, &RegistrationDialog::enableRegisterButton);
    connect(passwordInput, &QLineEdit::textChanged, this, &RegistrationDialog::enableRegisterButton);
    connect(confirmPasswordInput, &QLineEdit::textChanged, this, &RegistrationDialog::enableRegisterButton);
}

void RegistrationDialog::enableRegisterButton()
{
    registerButton->setEnabled(!usernameInput->text().isEmpty() &&
                               !emailInput->text().isEmpty() &&
                               !passwordInput->text().isEmpty() &&
                               !confirmPasswordInput->text().isEmpty() && 
                               passwordInput->text() == confirmPasswordInput->text() &&
                               passwordInput->text().length() >= 8 &&
                               emailInput->text().contains("@") &&
                               emailInput->text().contains("."));
}
