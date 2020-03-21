package com.tfar.ww1guns;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class GunItem extends Item {

	public static final DamageSource BULLET = new DamageSource("bullet").setDamageBypassesArmor();

	public int magazine_size = 1;
	public double damage = 1;

	public GunItem(GunSettings gunSettings){
		loadSettings(gunSettings);
	}

	public void loadSettings(GunSettings settings) {
		this.damage = settings.damage;
		this.magazine_size = settings.magazine_size;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {

		ItemStack itemstack = player.getHeldItem(handIn);
		if (!world.isRemote) {
			rayTrace(player)
							.ifPresent(entity -> entity.attackEntityFrom(BULLET,5));
		}
		player.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}

	public static Optional<Entity> rayTrace(EntityPlayer player){
			double distance = 64;
			Vec3d vec3d = player.getPositionEyes(1);
			Vec3d vec3d1 = player.getLook(1.0F);
			Vec3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
			Entity pointedEntity = null;
			List<Entity> list = player.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox()
											.expand(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance).grow(1.0D, 1.0D, 1.0D),
							Predicates.and(EntitySelectors.NOT_SPECTATING, entity -> entity != null && entity.canBeCollidedWith()));
			double d2 = distance;
			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = list.get(j);
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

				if (axisalignedbb.contains(vec3d)) {
					if (d2 >= 0.0D) {
						pointedEntity = entity1;
						d2 = 0.0D;
					}
				} else if (raytraceresult != null) {
					double d3 = vec3d.distanceTo(raytraceresult.hitVec);

					if (d3 < d2 || d2 == 0.0D) {
						if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity1.canRiderInteract()) {
							if (d2 == 0.0D) {
								pointedEntity = entity1;
							}
						} else {
							pointedEntity = entity1;
							d2 = d3;
						}
					}
				}
			}
			return Optional.ofNullable(pointedEntity);
		}
}
