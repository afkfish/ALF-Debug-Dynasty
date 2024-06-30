#include "BuyBookWindow.h"

BuyBookWindow::BuyBookWindow(QWidget *parent) : QMainWindow(parent) {
    layout = new QVBoxLayout();
    
    QStringList books = { "The Great Gatsby", "To Kill a Mockingbird", "1984", "Pride and Prejudice", "The Catcher in the Rye" };
    listBooks = new QListWidget();
    layout->addWidget(listBooks);
    listBooks->clear();
    listBooks->addItems(books);
    //label = new QLabel("Search for a book by title:");
    
    QWidget *window = new QWidget();
    window->setLayout(layout);
    setCentralWidget(window);

    connect(listBooks, &QListWidget::itemDoubleClicked, this, &BuyBookWindow::buyBook);
}

void BuyBookWindow::buyBook() {
    QMessageBox::StandardButton reply;
    reply = QMessageBox::question(this, "Purchase", "Are you sure you want to buy this book?",
                                  QMessageBox::Yes|QMessageBox::No);
    if (reply == QMessageBox::Yes) {
        QMessageBox::information(this, "Purchase", "You have bought a book!");
    } else {
        QMessageBox::information(this, "Purchase", "Purchase cancelled.");
    }
}