package uk.lobsterdoodle.sample.fixedscrollingtablayoutbanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import uk.lobsterdoodle.sample.fixedscrollingtablayoutbanner.MainActivity.CustomRecyclerViewAdapter.ViewHolder

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
        private lateinit var adapter: CustomRecyclerViewAdapter

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_sample, container, false)
            adapter = CustomRecyclerViewAdapter((1..20).map { "${arguments.get("header")}: Item #$it" })
            val recyclerView = view.findViewById<RecyclerView>(R.id.list_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(-1)) {
                        Log.d("onScroll", "can scroll: false")
                    }
                }
            })
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

    class CustomRecyclerViewAdapter(val data: List<String>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.listItemText.text = data[position]
        }

        override fun getItemCount(): Int = data.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent, false))

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var listItemText: TextView = view.findViewById(R.id.list_item_text)
        }
    }
}
