package hu.aut.bme.springbootalf;
import java.util.logging.Level;
import java.util.logging.Logger;
class ALFLogger {

    companion object {
        val logger: Logger = Logger.getLogger(ALFLogger::class.java.name)

        /**
         * Logs an error message.
         * @param message The error message to be logged.
         */
        fun logError(message: String) {
            logger.log(Level.SEVERE, message)
        }

        /**
         * Logs a warning message.
         * @param message The warning message to be logged.
         */
        fun logWarning(message: String) {
            logger.log(Level.WARNING, message)
        }

        /**
         * Logs an informational message.
         * @param message The informational message to be logged.
         */
        fun logInfo(message: String) {
            logger.log(Level.INFO, message)
        }

        /**
         * Logs a debug message.
         * @param message The debug message to be logged.
         */
        fun logDebug(message: String){
            logger.log(Level.ALL, message)
        }
    }

}
