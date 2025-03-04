package com.example.hardmad2024_1.screens

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hardmad2024_1.R
import com.example.hardmad2024_1.presentation.fragments.StatisticsFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.pager2.KViewPager2
import io.github.kakaocup.kakao.pager2.KViewPagerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import org.hamcrest.Matcher

object StatisticsScreen : KScreen<StatisticsScreen>() {
    val viewPager = KViewPager2(
        builder = { withId(R.id.viewPagerHorizontal) },
        itemTypeBuilder = {
            itemType(::PagerItem)
        }
    )

    override val layoutId: Int?
        get() = R.layout.statistics_fragment
    override val viewClass: Class<*>?
        get() = StatisticsFragment::class.java
}

class PagerItem(matcher: Matcher<View>) : KViewPagerItem<PagerItem>(matcher){

}