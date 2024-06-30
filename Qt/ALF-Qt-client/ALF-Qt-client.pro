QT += \
    core gui \
    widgets network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++17

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    main.cpp \
    mainwindow.cpp \
    registrationdialog.cpp \
    userlogindialog.cpp \
    adminlogindialog.cpp \
    userinterface.cpp \
    admininterface.cpp \
    bookdetails.cpp \
    buybookwindow.cpp

HEADERS += \
    mainwindow.h \
    registrationdialog.h \
    userlogindialog.h \
    adminlogindialog.h \
    userinterface.h \
    admininterface.h \
    bookdetails.h \
    buybookwindow.h

FORMS += \
    mainwindow.ui


# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target
