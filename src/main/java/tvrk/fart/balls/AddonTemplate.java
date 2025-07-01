package tvrk.fart.balls;

import tvrk.fart.balls.commands.CommandExample;
import tvrk.fart.balls.hud.HudExample;
import tvrk.fart.balls.modules.AntiKnockback;
import tvrk.fart.balls.modules.AutoSwastika;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;


public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Turkeor");
    public static final HudGroup HUD_GROUP = new HudGroup("Turkeor");

    @Override
    public void onInitialize() {
        LOG.info("Solid, liquid, gas - they all come out my ass");

        Modules.get().add(new AutoSwastika());
        Modules.get().add(new AntiKnockback());

        Commands.add(new CommandExample());

        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "tvrk.fart.balls";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("kibular", "turkeor");
    }
}
