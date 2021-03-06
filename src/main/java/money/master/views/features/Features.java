package money.master.views.features;

import com.vaadin.flow.component.Text;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import money.master.views.main.HomeView;

@Route(value = "features", layout = HomeView.class)
@HtmlImport("frontend://src/features.html")
public class Features extends VerticalLayout {
    public Features() {
       add(new H1("Application Features"));
       add(new Text("Along with coming with standard finance application features such as a budget calculator" +
               " and tips on improving spending, MoneyMaster has several highlights:"));
        /*
        these highlights will be in a unordered list: opportunity seeker, benefits and programs customized to degree,
        perhaps
         */

    }
}
