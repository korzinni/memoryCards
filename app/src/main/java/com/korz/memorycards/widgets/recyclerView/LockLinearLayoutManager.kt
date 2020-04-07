import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class LockLinearLayoutManager(context: Context) : LinearLayoutManager(context, VERTICAL, false) {
    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}