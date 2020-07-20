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
import org.jpac.OutputInterlockException;
import org.jpac.PeriodOfTime;
import org.jpac.ProcessException;
import org.jpac.SignalAlreadyExistsException;
import org.jpac.WrongUseException;
import org.jpac.vioss.IoLogical;

/**
 *
 * @author berndschuster
 */
public class TogglePinModule extends Module{
    
    private IoLogical pin4;
    
    public TogglePinModule(AbstractModule containingModule, String identifier) throws SignalAlreadyExistsException, InconsistencyException, WrongUseException, URISyntaxException{
        super(containingModule, identifier);
        pin4 = new IoLogical(this, "RaspiOutputPin04", new URI("pi.gpio:/4"), IoDirection.OUTPUT);
    }
    
    @Override
    protected void work() throws ProcessException {
        PeriodOfTime toggleTime         = new PeriodOfTime(1 * sec);//instantiate a period of time event (1 s)
        PeriodOfTime extendedToggleTime = new PeriodOfTime(3 * sec);//instantiate a period of time event (1 s)
        int          cycleCounter       = 0;
        Log.info("started ...");
        pin4.set(false);
        do{
            if (++cycleCounter % 10 != 0){
                toggleTime.await();//wait for 1 second
            }
            else{
                Log.info("delaying toggle ...");
                extendedToggleTime.await();
            }
            pin4.set(!pin4.is(true));
            //Log.info("toggled " + pin4);
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
