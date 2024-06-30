#ifndef ADDININTERFACE_H
#define ADDININTERFACE_H

#include <QMainWindow>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>

class AdminInterface : public QMainWindow {
    Q_OBJECT
public:
    explicit AdminInterface(QWidget *parent = nullptr);

private:
    QLabel *adminLabel;
    QPushButton *adminButton;
    QVBoxLayout *adminLayout;
};

#endif 