#include "admininterface.h"

AdminInterface::AdminInterface(QWidget *parent) : QMainWindow(parent) {
    QWidget *centralWidget = new QWidget(this);
    setCentralWidget(centralWidget);

    adminLayout = new QVBoxLayout(centralWidget);

    adminLabel = new QLabel("Welcome to the admin Interface!", this);
    adminLayout->addWidget(adminLabel);

    adminButton = new QPushButton("Click me!", this);
    adminLayout->addWidget(adminButton);

    this->setWindowTitle("admin Dashboard");
}