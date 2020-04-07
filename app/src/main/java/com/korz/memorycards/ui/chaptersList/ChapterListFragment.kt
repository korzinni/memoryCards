package com.korz.memorycards.ui.chaptersList

import android.os.Bundle
import com.korz.memorycards.R
import com.korz.memorycards.databinding.FragmentChaptersListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.id_east.gm.ui.common.BaseFragment

class ChapterListFragment : BaseFragment<FragmentChaptersListBinding>() {
    override val layoutId = R.layout.fragment_chapters_list

    private val viewModel: ChaptersListViewModel by viewModel()
    override fun onViewCreated(binding: FragmentChaptersListBinding, savedInstanceState: Bundle?) {

    }

}
