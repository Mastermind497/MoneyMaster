package money.master.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import money.master.views.budget.BudgetView;
import money.master.views.learn.LearnView;

import java.util.*;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "MoneyMaster", shortName = "MoneyMaster")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainView extends AppLayout {

    private final Tabs menu;
    public final MenuBar profile = new MenuBar();

    public MainView() {
        setPrimarySection(Section.DRAWER);
    
        HorizontalLayout profileLayout = new HorizontalLayout();
        profileLayout.setPadding(false);
        profileLayout.setMargin(false);
        profileLayout.add(new Icon(VaadinIcon.USER));
        profileLayout.add("Profile");

        profile.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

        MenuItem profileItem    = profile.addItem(profileLayout);
        SubMenu  profileSubMenu = profileItem.getSubMenu();
        profileSubMenu.addItem("Update Profile", onClick -> UI.getCurrent().navigate(Profile.class));
        profileSubMenu.add(new Hr());
        profileSubMenu.addItem("Sign Out", onClick -> UI.getCurrent().navigate(Home.class));
        
        HorizontalLayout navBar = new HorizontalLayout();
        navBar.setPadding(true);
        navBar.setHeightFull();
        navBar.add(new DrawerToggle());
        
        HorizontalLayout spacer = new HorizontalLayout(new H1(""));
        spacer.setHeight("1%");
        spacer.setWidth("90%");
        navBar.add(spacer);
        navBar.add(profile);
        navBar.setWidthFull();
        
        addToNavbar(navBar);
        
        menu = createMenuTabs();
        addToDrawer(menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Budgeter", BudgetView.class));
        tabs.add(createTab("Learn", LearnView.class));
        return tabs.toArray(new Tab[0]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }
}
