package uk.lobsterdoodle.sample.fixedscrollingtablayoutbanner

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.Arrays.asList

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager>(R.id.pager)
        pager.adapter = MainActivityPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(pager)
    }

    private inner class MainActivityPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        val titles = listOf("Fragment A", "Fragment B", "Fragment C")

        override fun getItem(position: Int): Fragment = CustomFragment.instance(titles[position])

        override fun getPageTitle(position: Int): CharSequence = titles[position]

        override fun getCount(): Int = titles.size
    }

    class CustomFragment : Fragment() {
        private lateinit var adapter: ArrayAdapter<String>

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater!!.inflate(R.layout.fragment_sample, container, false)
            adapter = ArrayAdapter(context, R.layout.my_list_item, (1..20).map { "${arguments.get("header")}: Item #$it" })
            view.findViewById<ListView>(R.id.list_view).adapter = adapter
            return view
        }

        companion object {
            fun instance(header: String): Fragment {
                val frag = CustomFragment()
                val args = Bundle()
                args.putString("header", header)
                frag.arguments = args
                return frag
            }
        }
    }
}
