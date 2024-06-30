#ifndef BUYBOOKWINDOW_H
#define BUYBOOKWINDOW_H

#include <QMainWindow>
#include <QPushButton>
#include <QVBoxLayout>
#include <QMessageBox>
#include <QLabel>
#include <QLineEdit>
#include <QListWidget>

class BuyBookWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit BuyBookWindow(QWidget *parent = nullptr);

public slots:
    void buyBook();

private:
    QVBoxLayout *layout;
    QListWidget *listBooks;
};

#endif // BUYBOOKWINDOW_H