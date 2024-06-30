#include "BookDetails.h"

BookDetails::BookDetails(QWidget *parent) : QMainWindow(parent) {
    labelBook = new QLabel(this);
    layout = new QVBoxLayout();

    layout->addWidget(labelBook);

    QWidget *window = new QWidget();
    window->setLayout(layout);
    setCentralWidget(window);
}

void BookDetails::setBook(const QString &book) {
    labelBook->setText(book);
}