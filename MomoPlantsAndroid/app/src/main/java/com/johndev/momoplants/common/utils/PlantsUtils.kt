package com.johndev.momoplants.common.utils

import android.content.Context
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.ERROR_EQUALS
import com.johndev.momoplants.common.utils.Constants.ERROR_EXIST

fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {
    fragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commit()
}

fun printToastMsg(msgRes: Int, context: Context) =
    Toast.makeText(context, context.getText(msgRes), Toast.LENGTH_SHORT).show()

fun printToastWithStringMsg(msgRes: Int, value: String, context: Context) =
    Toast.makeText(context, context.getString(msgRes, value), Toast.LENGTH_SHORT).show()

fun printSnackbarMsg(view: View, msgRes: Int, context: Context) =
    Snackbar.make(view, context.getString(msgRes), Snackbar.LENGTH_SHORT).show()

fun validFields(fields: List<Pair<TextInputEditText, TextInputLayout>>, context: Context): Boolean {
    var isValid = true
    for ((field, layout) in fields) {
        if (field.text.isNullOrEmpty()) {
            layout.run {
                error = context.getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            layout.error = null
        }
    }
    return isValid
}

fun Toolbar.setupNavigationTo(fragment: Fragment, fragmentManager: FragmentManager) {
    setNavigationOnClickListener {
        openFragment(
            fragment = fragment,
            fragmentManager = fragmentManager,
            containerId = R.id.container
        )
    }
}

fun setupImage(imgCover: ImageView, imgRes: Int) {
    imgCover.load(imgRes) {
        crossfade(true)
        placeholder(R.drawable.ic_broken_image)
        //transformations(CircleCropTransformation())
    }
}

fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun getMsgErrorByCode(errorCode: String?): Int = when (errorCode) {
    ERROR_EXIST -> R.string.error_unique_code
    else -> R.string.error_unknow
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getAllPlants() = listOf(
    PlantEntity(
        plant_id = 0,
        name = "Monstera deliciosa",
        description = "También conocida como costilla de Adán, es una planta trepadora que se caracteriza por sus grandes hojas de color verde oscuro y forma de corazón.",
        image = R.drawable.bonsai,
        origin = "América Central y del Sur",
        weather = "Cálido y húmedo",
        format = "Maceta de 15 cm",
        price = 25,
        stock = 10,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 1, name = "Ficus elastica",
        description = "El árbol del caucho es una planta que puede llegar a alcanzar varios metros de altura, sus hojas son grandes y de color verde oscuro y brillante.",
        image = R.drawable.bonsai,
        origin = "India y Malasia",
        weather = "Cálido y húmedo",
        format = "Maceta de 20 cm",
        price = 40,
        stock = 5,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 2, name = "Pilea peperomioides",
        description = "Conocida también como la planta china del dinero, tiene hojas redondas y suculentas, y crece en pequeñas agrupaciones.",
        image = R.drawable.bonsai,
        origin = "China",
        weather = "Templado",
        format = "Maceta de 10 cm",
        price = 15,
        stock = 15,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 3, name = "Dracaena marginata",
        description = "Es una planta de hojas largas y estrechas que crecen en forma de espada. Sus hojas son de color verde oscuro con bordes rojos.",
        image = R.drawable.bonsai,
        origin = "Madagascar",
        weather = "Templado",
        format = "Maceta de 12 cm",
        price = 20,
        stock = 8,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 4, name = "Sansevieria trifasciata",
        description = "También conocida como lengua de suegra, es una planta resistente y fácil de cuidar. Sus hojas son largas y puntiagudas, de color verde oscuro con rayas amarillas.",
        image = R.drawable.bonsai,
        origin = "África",
        weather = "Templado",
        format = "Maceta de 10 cm",
        price = 12,
        stock = 20,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 5, name = "Calathea orbifolia",
        description = "Esta planta se caracteriza por sus hojas grandes y redondas, con una coloración verde oscuro y unas líneas plateadas que las atraviesan.",
        image = R.drawable.bonsai,
        origin = "América del Sur",
        weather = "Cálido y húmedo",
        format = "Maceta de 14 cm",
        price = 35,
        stock = 4,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 6, name = "Alocasia macrorrhiza",
        description = "También conocida como oreja de elefante, es una planta tropical de hojas grandes y brillantes, de forma acorazonada y con venas marcadas.",
        image = R.drawable.bonsai,
        origin = "Sudeste Asiático",
        weather = "Cálido y húmedo",
        format = "Maceta de 20 cm",
        price = 45,
        stock = 7,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 7, name = "Pachira aquatica",
        description = "También conocida como árbol del dinero o castaña de agua, es un árbol tropical con hojas compuestas y flores blancas. Se considera una planta de buena suerte en algunas culturas.",
        image = R.drawable.bonsai,
        origin = "América del Sur y Central",
        weather = "Cálido y húmedo",
        format = "Maceta de 18 cm",
        price = 30,
        stock = 12,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 8, name = "Echeveria elegans",
        description = "Es una suculenta de hojas gruesas y carnosas, de color verde azulado y forma de roseta. Es una planta fácil de cuidar y muy decorativa.",
        image = R.drawable.bonsai,
        origin = "México",
        weather = "Cálido y seco",
        format = "Maceta de 8 cm",
        price = 10,
        stock = 25,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 9, name = "Haworthia fasciata",
        description = "Es una pequeña suculenta con hojas carnosas y transparentes, con bandas blancas en la parte superior. Es una planta resistente y fácil de cuidar.",
        image = R.drawable.bonsai,
        origin = "Sudáfrica",
        weather = "Templado y seco",
        format = "Maceta de 10 cm",
        price = 8,
        stock = 30,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 10, name = "Strelitzia reginae",
        description = "También conocida como ave del paraíso, es una planta tropical con hojas largas y flores naranjas y azules. Es una planta muy vistosa y ornamental.",
        image = R.drawable.bonsai,
        origin = "Sudáfrica",
        weather = "Cálido y húmedo",
        format = "Maceta de 20 cm",
        price = 55,
        stock = 5,
        quantity = 0
    ),
    PlantEntity(
        plant_id = 11, name = "Aglaonema commutatum",
        description = "Es una planta tropical de hojas grandes y brillantes, de color verde oscuro y bordes plateados. Es una planta de interior muy popular por su facilidad de cuidado.",
        image = R.drawable.bonsai,
        origin = "Sudeste Asiático",
        weather = "Cálido y húmedo",
        format = "Maceta de 14 cm",
        price = 25,
        stock = 15,
        quantity = 0
    ),
)