import android.content.Context
import android.content.Intent
import android.net.Uri

fun openGoogleMaps(context: Context) {
    val address = "C. de la Barca del Bou, 6, 03503 Benidorm, Alicante"
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}")
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}