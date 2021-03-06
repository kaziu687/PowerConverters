package covers1624.powerconverters.charge;

import covers1624.powerconverters.api.charge.IItemChargeHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.registry.PowerSystemRegistry.PowerSystem;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;

public class ItemChargeHandlerIndustrialCraft implements IItemChargeHandler {
    @Override
    public PowerSystem getPowerSystem() {
        return PowerSystems.powerSystemIndustrialCraft;
    }

    @Override
    public boolean canHandle(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof IElectricItem)) {
            return false;
        }
        return true;
    }

    @Override
    public double charge(ItemStack stack, double energyInput) {
        double eu = energyInput / PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
        eu -= ElectricItem.manager.charge(stack, eu, ((IElectricItem) stack.getItem()).getTier(stack), false, false);
        return eu * PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
    }

    @Override
    public double discharge(ItemStack stack, double energyRequest) {
        double eu = energyRequest / PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
        eu = ElectricItem.manager.discharge(stack, eu, ((IElectricItem) stack.getItem()).getTier(stack), false, false, false);
        return eu * PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
    }

    @Override
    public String name() {
        return "Industrial Craft";
    }

    @Override
    public boolean isItemCharged(ItemStack stack) {
        if (canHandle(stack)) {
            IElectricItem item = (IElectricItem) stack.getItem();
            if (item.getMaxCharge(stack) == ElectricItem.manager.getCharge(stack)) {
                return true;
            }
        }
        return false;
    }
}
