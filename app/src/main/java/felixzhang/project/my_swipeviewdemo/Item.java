package felixzhang.project.my_swipeviewdemo;

/**
 * Created by felix on 15/4/26.
 */
public class Item {
    String content;
    String delete;

    public Item(String content, String delete) {
        this.content = content;
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "Item{" +
                "content='" + content + '\'' +
                ", delete='" + delete + '\'' +
                '}';
    }
}
