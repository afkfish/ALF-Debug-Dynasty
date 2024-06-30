#ifndef BOOKDETAILS_H
#define BOOKDETAILS_H

#include <QMainWindow>
#include <QLabel>
#include <QVBoxLayout>
#include <QString>

class BookDetails : public QMainWindow {
    Q_OBJECT

public:
    explicit BookDetails(QWidget *parent = nullptr);
    void setBook(const QString &book);

private:
    QLabel *labelBook;
    QVBoxLayout *layout;
};

#endif // BOOKDETAILS_H