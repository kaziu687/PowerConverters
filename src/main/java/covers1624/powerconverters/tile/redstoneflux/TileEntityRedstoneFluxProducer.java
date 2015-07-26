package covers1624.powerconverters.tile.redstoneflux;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.tile.main.TileEntityEnergyProducer;
import covers1624.powerconverters.util.BlockPosition;

public class TileEntityRedstoneFluxProducer extends TileEntityEnergyProducer<IEnergyHandler> implements IEnergyHandler {

	public TileEntityRedstoneFluxProducer() {
		super(PowerSystems.powerSystemRedstoneFlux, 0, IEnergyHandler.class);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection arg0) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection arg0) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		return (int) (bridge.getEnergyStored() / getPowerSystem().getScaleAmmount());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getScaleAmmount());
	}

	@Override
	public int receiveEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
		return 0;
	}

	@Override
	public double produceEnergy(double energy) {
		final double toUseRF = energy / getPowerSystem().getScaleAmmount();

		if (toUseRF > 0) {
			List<BlockPosition> pos = new BlockPosition(xCoord, yCoord, zCoord).getAdjacent(true);
			for (BlockPosition p : pos) {
				TileEntity te = worldObj.getTileEntity(p.x, p.y, p.z);
				if ((te instanceof IEnergyHandler) && !((te instanceof TileEntityRedstoneFluxConsumer) || (te instanceof TileEntityEnergyBridge))) {
					IEnergyHandler handler = (IEnergyHandler) te;
					final double RF = handler.receiveEnergy(p.orientation.getOpposite(), (int) (toUseRF), false);
					energy -= RF * getPowerSystem().getScaleAmmount();
					if (energy <= 0)
						break;
				}
			}

		}
		return energy;
	}

}
