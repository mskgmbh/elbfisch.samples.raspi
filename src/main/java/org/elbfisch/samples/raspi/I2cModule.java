/**
 * PROJECT   : Elbfisch - java process automation controller (jPac)
 * MODULE    : TogglePinModule.java
 * VERSION   : -
 * DATE      : -
 * PURPOSE   : 
 * AUTHOR    : Bernd Schuster, MSK Gesellschaft fuer Automatisierung mbH, Schenefeld
 * REMARKS   : -
 * CHANGES   : CH#n <Kuerzel> <datum> <Beschreibung>
 *
 * This file is part of the jPac process automation controller.
 * jPac is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jPac is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the jPac If not, see <http://www.gnu.org/licenses/>.
 */

package org.elbfisch.samples.raspi;

import java.net.URI;
import java.net.URISyntaxException;
import org.jpac.AbstractModule;
import org.jpac.InconsistencyException;
import org.jpac.InputInterlockException;
import org.jpac.IoDirection;
import org.jpac.Module;
import org.jpac.NextCycle;
import org.jpac.OutputInterlockException;
import org.jpac.PeriodOfTime;
import org.jpac.ProcessException;
import org.jpac.SignalAlreadyExistsException;
import org.jpac.SignedInteger;
import org.jpac.WrongUseException;
import org.jpac.vioss.IoSignedInteger;

/**
 *
 * @author berndschuster
 */
public class I2cModule extends Module{
	private final int DEVICEADDR = 0x70;
	
    
    private SignedInteger   tca9548aOut;
    private SignedInteger   tca9548aIn;
    
    public I2cModule(AbstractModule containingModule, String identifier) throws SignalAlreadyExistsException, InconsistencyException, WrongUseException, URISyntaxException{
        super(containingModule, identifier);
        //URI: "pi.i2c:/<bus>/<device>/<datasize>/<byte address>[/<bit address>][?endianess=bigendian | littleendian]"
        this.tca9548aOut = new IoSignedInteger(this, "tca9548aOut", new URI("pi.i2c:/1/" + DEVICEADDR + "/1/0"), IoDirection.OUTPUT);
        this.tca9548aIn  = new IoSignedInteger(this, "tca9548aIn", new URI("pi.i2c:/1/" + DEVICEADDR + "/1/0"), IoDirection.INPUT);
    }
    
    @Override
    protected void work() throws ProcessException {
        PeriodOfTime toggleTime  = new PeriodOfTime(1 * sec);//instantiate a period of time event (1 s)
        NextCycle    nc          = new NextCycle();
        int          i           = 0;
        Log.info("started ...");
        do{
        	tca9548aOut.set(i++ % 8);
        	nc.await();
        	Log.info("tca9548aOut = " + tca9548aOut.get() + " tca9548aIn = " + tca9548aIn.get());
            toggleTime	.await();
        }
        while(true);    
    }

    @Override
    protected void preCheckInterlocks() throws InputInterlockException {
    }

    @Override
    protected void postCheckInterlocks() throws OutputInterlockException {
    }

    @Override
    protected void inEveryCycleDo() throws ProcessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
