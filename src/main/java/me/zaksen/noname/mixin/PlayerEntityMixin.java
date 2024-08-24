package me.zaksen.noname.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract ServerWorld getServerWorld();

    @Inject(at = @At("HEAD"), method = "onSpawn")
    private void onSpawn(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if(this.getServerWorld().getScoreboard().getTeam("players_team") == null) {
            Team playersTeam = this.getServerWorld().getScoreboard().addTeam("players_team");
            playersTeam.setNameTagVisibilityRule(AbstractTeam.VisibilityRule.NEVER);
            playersTeam.setShowFriendlyInvisibles(false);
        }

        Team playersTeam = this.getServerWorld().getScoreboard().getTeam("players_team");
        this.getServerWorld().getScoreboard().addPlayerToTeam(player.getName().getString(), playersTeam);
    }

}
