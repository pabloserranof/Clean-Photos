package com.pabloserrano.cleancomponents.cache.db.constants

/**
 * Defines constants for the Photo Table
 */
object PhotoConstants {

    const val TABLE_NAME = "photos"

    const val QUERY_PHOTOS = "SELECT * FROM" + " " + TABLE_NAME

    const val DELETE_ALL_PHOTOS = "DELETE FROM" + " " + TABLE_NAME

}