// UserInterface.h
#ifndef USERINTERFACE_H
#define USERINTERFACE_H

#include <QMainWindow>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>
#include <QListWidget>

class UserInterface : public QMainWindow {
    Q_OBJECT

private:
    double userBalance = 0.0;
    QStringList userBooks;

private slots:
    void buyBook();
    void openBookWindow();
    void changePassword();

public:
    explicit UserInterface(QWidget *parent = nullptr);

private:
    QLabel *labelBalance;
    QPushButton *buttonBuyBook;
    QPushButton *buttonChangePassword;
    QVBoxLayout *layout;
    QListWidget *listBooks; // Add this line
};

#endif // USERINTERFACE_H