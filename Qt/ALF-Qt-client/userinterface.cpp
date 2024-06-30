#include "UserInterface.h"
#include "BookDetails.h"
#include "BuyBookWindow.h"
#include <QInputDialog>
#include <QFormLayout>

UserInterface::UserInterface(QWidget *parent) : QMainWindow(parent) {
    userBooks = {"The Great Gatsby", "To Kill a Mockingbird", "1984", "Pride and Prejudice", "The Catcher in the Rye"};

    labelBalance = new QLabel(this);
    buttonBuyBook = new QPushButton(this);
    buttonChangePassword = new QPushButton(this);
    buttonBuyBook->setText("Buy Book");
    buttonChangePassword->setText("Change Password");
    layout = new QVBoxLayout(this);
    listBooks = new QListWidget(this);

    layout->addWidget(labelBalance);
    layout->addWidget(buttonBuyBook);
    layout->addWidget(listBooks);
    layout->addWidget(buttonChangePassword);
    QWidget *window = new QWidget();
    window->setLayout(layout);
    setCentralWidget(window);

    // Update the balance
    labelBalance->setText(QString("Balance: %1").arg(userBalance));

    // Update the list of books
    listBooks->clear();
    listBooks->addItems(userBooks);
    connect(listBooks, &QListWidget::itemDoubleClicked, this, &UserInterface::openBookWindow);
    connect(buttonBuyBook, &QPushButton::clicked, this, &UserInterface::buyBook);
    connect(buttonChangePassword, &QPushButton::clicked, this, &UserInterface::changePassword); // Connect the button to the slot
}

void UserInterface::openBookWindow() {
    // Get the selected book
    QListWidgetItem *selectedItem = listBooks->currentItem();
    if (!selectedItem) {
        // No book is selected
        return;
    }
    QString selectedBook = selectedItem->text();

    // Create a new BookDetails window and set the selected book
    BookDetails *details = new BookDetails(this);
    details->setBook(selectedBook);

    // Show the window
    details->show();
}
void UserInterface::buyBook() {
    BuyBookWindow *buyWindow = new BuyBookWindow(this);
    buyWindow->show();
}
void UserInterface::changePassword() {
    QDialog dialog(this);
    QFormLayout form(&dialog);

    // Add old password field
    QLineEdit *oldPassword = new QLineEdit(&dialog);
    oldPassword->setEchoMode(QLineEdit::Password);
    form.addRow(new QLabel("Old Password:"), oldPassword);

    // Add new password field
    QLineEdit *newPassword = new QLineEdit(&dialog);
    newPassword->setEchoMode(QLineEdit::Password);
    form.addRow(new QLabel("New Password:"), newPassword);

    // Add confirm new password field
    QLineEdit *confirmNewPassword = new QLineEdit(&dialog);
    confirmNewPassword->setEchoMode(QLineEdit::Password);
    form.addRow(new QLabel("Confirm New Password:"), confirmNewPassword);

    // Add buttons
    QDialogButtonBox buttonBox(QDialogButtonBox::Ok | QDialogButtonBox::Cancel, Qt::Horizontal, &dialog);
    form.addRow(&buttonBox);

    // Disable Ok button initially
    buttonBox.button(QDialogButtonBox::Ok)->setEnabled(false);

    // Enable Ok button only if new passwords match
    connect(newPassword, &QLineEdit::textChanged, [&](){
        buttonBox.button(QDialogButtonBox::Ok)->setEnabled(newPassword->text() == confirmNewPassword->text());
    });
    connect(confirmNewPassword, &QLineEdit::textChanged, [&](){
        buttonBox.button(QDialogButtonBox::Ok)->setEnabled(newPassword->text() == confirmNewPassword->text());
    });

    connect(&buttonBox, &QDialogButtonBox::accepted, &dialog, &QDialog::accept);
    connect(&buttonBox, &QDialogButtonBox::rejected, &dialog, &QDialog::reject);

    if (dialog.exec() == QDialog::Accepted) {
        QMessageBox::information(this, "Change Password", "Password changed successfully!");
    }
}

