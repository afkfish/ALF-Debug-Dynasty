#include <QApplication>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>
#include <QDialog>
#include <QMainWindow>
#include <QLineEdit>
#include "registrationdialog.h"
#include "userlogindialog.h"
#include "adminlogindialog.h"

int main(int argc, char **argv)
{
    QApplication app(argc, argv);

    QMainWindow mainWindow;
    mainWindow.setWindowTitle("Welcome");

    QVBoxLayout layout;

    QLabel welcomeLabel("Welcome to our application!", &mainWindow);
    layout.addWidget(&welcomeLabel);

    QPushButton userLoginButton("User Login", &mainWindow);
    layout.addWidget(&userLoginButton);

    QPushButton adminLoginButton("Admin Login", &mainWindow);
    layout.addWidget(&adminLoginButton);

    QPushButton registrationButton("Registration", &mainWindow);
    layout.addWidget(&registrationButton);

    QWidget centralWidget(&mainWindow);
    centralWidget.setLayout(&layout);
    mainWindow.setCentralWidget(&centralWidget);

    QObject::connect(&userLoginButton, &QPushButton::clicked, &mainWindow, [&]() {
        UserLoginDialog userLoginDialog(&mainWindow);
        QObject::connect(&userLoginDialog, &UserLoginDialog::loginSuccessful, &mainWindow, &QMainWindow::close);
        mainWindow.setWindowTitle("User Dashboard");
        userLoginDialog.exec();
    });
    
    QObject::connect(&adminLoginButton, &QPushButton::clicked, &mainWindow, [&]() {
        AdminLoginDialog adminLoginDialog(&mainWindow);
        QObject::connect(&adminLoginDialog, &AdminLoginDialog::loginSuccessful, &mainWindow, &QMainWindow::close);
        mainWindow.setWindowTitle("Admin Dashboard");
        adminLoginDialog.exec();
    });
    
    
    QObject::connect(&registrationButton, &QPushButton::clicked, &mainWindow, [&]() {
        RegistrationDialog registrationDialog(&mainWindow);
        registrationDialog.exec();
    });
    

    mainWindow.show();

    return app.exec();
}
