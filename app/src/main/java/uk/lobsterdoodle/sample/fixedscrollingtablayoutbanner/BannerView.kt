package uk.lobsterdoodle.sample.fixedscrollingtablayoutbanner

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.FrameLayout

@ViewPager.DecorView
class BannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_banner, this)
    }
}