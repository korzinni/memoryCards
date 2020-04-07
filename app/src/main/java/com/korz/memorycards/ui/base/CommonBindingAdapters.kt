package com.korz.memorycards.ui.base

import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.korz.memorycards.R
import ru.id_east.gm.utils.DimensionUtils
import java.math.BigDecimal


object CommonBindingAdapters {
    @JvmStatic
    @BindingAdapter("clipToOutline")
    fun setClipToOutline(view: View, boolean: Boolean) {
        view.clipToOutline = boolean
    }

    @JvmStatic
    @BindingAdapter("urlImage")
    fun setUrlImage(view: ImageView, url: String?) {
        Glide
            .with(view)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.round_solid_gray_rect)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("urlImageProduct")
    fun setUrlImageProduct(view: ImageView, url: String?) {
        Glide
            .with(view)
            .load(url)
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("flagImage")
    fun setSVGImage(view: ImageView, url: String?) {

    }

    @JvmStatic
    @BindingAdapter("urlImageRest")
    fun setUrlImageRest(view: ImageView, url: String?) {
        Glide
            .with(view)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.round_solid_gray_rect)
//            .fallback(R.drawable.establishment_preview_stub)
//            .error(R.drawable.establishment_preview_stub)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("recyclerOrientation")
    fun setRecyclerOrientation(view: RecyclerView, orientation: Int) {
        view.layoutManager = LinearLayoutManager(view.context, orientation, false)
    }

    @JvmStatic
    @BindingAdapter("bindingFontFamily")
    fun setRecyclerOrientation(view: TextView, fontFamily: String?) {
        if (fontFamily != null)
            view.typeface = Typeface.create(fontFamily, Typeface.NORMAL)
    }


    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("activated")
    fun setActivated(view: View, activated: Boolean) {
        view.isActivated = activated
    }

    @JvmStatic
    @BindingAdapter("setFlexBoxLayoutManager")
    fun setFlexBoxLayout(view: RecyclerView, flag: Boolean) {
        if (flag) {
            val layoutManager = FlexboxLayoutManager(view.context)
            layoutManager.flexDirection = FlexDirection.ROW
            view.setLayoutManager(layoutManager)
        }
    }

    @JvmStatic
    @BindingAdapter("clickableLink")
    fun setClickableLink(view: TextView, link: String?) {
        if (link == null)
            return
        val uri = Uri.parse(if (link.contains("http")) link else "https://$link")
        val text = "<a href=\"${uri}\">${uri.host}</a>"
        view.movementMethod = LinkMovementMethod.getInstance()
        view.text = Html.fromHtml(text)

    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun htmlText(textView: TextView, text: String?) {
        if (text == null) {
            return
        }
        textView.movementMethod = LinkMovementMethod.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(text)
        }
    }

    @JvmStatic
    @BindingAdapter("htmlString")
    fun setUrlImage(webView: WebView, data: String?) {
        if (data == null)
            return
        val head =
            "<head><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no'><style>img{max-width: 100%; width:auto; height: auto;}</style></head>"
        val extendData = "<html>$head<body>$data</body></html>"
        webView.settings.javaScriptEnabled = true
        webView.loadDataWithBaseURL("", extendData, "text/html", "UTF-8", "")
    }

    @JvmStatic
    @BindingAdapter(value = arrayOf("sum", "currency"))
    fun setSumAndCurrency(view: TextView, sum: BigDecimal?, currency: String?) {
        if (sum == null)
            return
        view.text = "$sum ${currency ?: ""}"
    }

    @JvmStatic
    @BindingAdapter("setGridLayoutManager")
    fun setGridLayoutManager(view: RecyclerView, weight: Int) {

        val layoutManager = GridLayoutManager(view.context, 60)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return weight
            }
        }
        view.setLayoutManager(layoutManager)
        view.invalidateItemDecorations()
    }

    @JvmStatic
    @BindingAdapter("angle")
    fun rotateView(view: View, degree: Float) {
        view.rotation = degree
    }

    @JvmStatic
    @BindingAdapter("behindStatusBar")
    fun isetsHandler(view: View, flag: Boolean) {
        if (flag)
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    this.topMargin = -insets.systemWindowInsetTop
                    v.requestLayout()
                }
                return@setOnApplyWindowInsetsListener insets
            }
    }

    @JvmStatic
    @BindingAdapter("navigationBarBottomMargin")
    fun bottomMargin(view: View, flag: Boolean) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            this.bottomMargin = DimensionUtils.getNavigationBarSize(view.context).y * 2
        }
    }
}