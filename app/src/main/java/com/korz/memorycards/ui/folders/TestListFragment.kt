package com.korz.memorycards.ui.folders

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.korz.memorycards.R
import com.korz.memorycards.databinding.FragmentTestListBinding
import com.korz.memorycards.widgets.recyclerView.SimpleRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.id_east.gm.ui.common.BaseFragment
import ru.id_east.gm.ui.common.setDumbClickListener
import ru.id_east.gm.utils.notNullObserve

class TestListFragment : BaseFragment<FragmentTestListBinding>() {
    override val layoutId = R.layout.fragment_test_list

    private val viewModel: FoldersViewModel by viewModel()

    val adapter = SimpleRecyclerAdapter<FoldersViewModel.FolderItem>(
        R.layout.item_fodler_for_animator,
        l = { view, folderItem, i ->
      //      val motionLayout = view as MotionLayout

//            val progress = motionLayout.progress
//            val target = 1 - progress
//            val animator = ValueAnimator.ofFloat(progress, target)
//            animator.addUpdateListener {
//                motionLayout.progress = it.animatedValue as Float
//            }
//
//            animator.start()
            viewModel.toggleFolder(folderItem)
        },
        compareObject = { o1, o2 -> o1.id == o2.id },
        compareContent = { o1, o2 -> o1.id == o2.id && o1.extend == o2.extend })

    override fun onViewCreated(
        binding: FragmentTestListBinding,
        savedInstanceState: Bundle?
    ) {

        binding.childFoldersList.adapter = adapter
        binding.childFoldersList.itemAnimator = FolderAnimator()
        viewModel.folders.notNullObserve(viewLifecycleOwner) {
            adapter.setDifferenceItems(it)
        }

        binding.text.setDumbClickListener {
            val progress = binding.motionLayout.progress
            val target = 1 - progress
            val animator = ValueAnimator.ofFloat(progress, target)
            animator.addUpdateListener {
                binding.motionLayout
                    .progress = it.animatedValue as Float
                //binding.motionLayout.updateState()
                Log.d("MOTION_L", "${binding.motionLayout.height}")
            }
            animator.start()
        }

        viewModel.requestFolders()
    }


}
