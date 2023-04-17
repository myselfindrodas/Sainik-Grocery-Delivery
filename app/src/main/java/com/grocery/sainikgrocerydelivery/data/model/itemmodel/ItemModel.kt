package com.grocery.sainikgrocerydelivery.data.model.itemmodel

class ItemModel(
    var itemname: String,
    var itemsize: String,
    var isReturnNoteShown: Boolean=false,
    var isReturned: Boolean=false
)