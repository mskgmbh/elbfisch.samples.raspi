/**
 * PROJECT   : Elbfisch - java process automation controller (jPac)
 * MODULE    : ReadBackPinModule.java
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
import org.jpac.EventTimedoutException;
import org.jpac.InconsistencyException;
import org.jpac.InputInterlockException;
import org.jpac.IoDirection;
import org.jpac.Module;
import org.jpac.OutputInterlockException;
import org.jpac.ProcessException;
import org.jpac.ShutdownRequestException;
import org.jpac.SignalAlreadyExistsException;
import org.jpac.WrongUseException;
import org.jpac.vioss.IoLogical;

/**
 *
 * @author berndschuster
 */
public class ReadBackPinModule extends Module{
    
    private IoLogical  pin5;
    
    public ReadBackPinModule(AbstractModule containingModule, String identifier) throws WrongUseException, URISyntaxException, SignalAlreadyExistsException, InconsistencyException{
        super(containingModule, identifier);
        pin5 = new IoLogical(this, "RaspiInputPin05", new URI("pi.gpio:/5"), IoDirection.INPUT);
    }
    
    @Override
    protected void work() throws ProcessException {
        Log.info("started ...");
        try{
        pin5.becomesValid().await();//wait, until the signal pin5 becomes valid
        do{
            try{
            Log.info("pin #5: "  + pin5);
            pin5.toggles().await(2 * sec);//wait, until pin5 changes it's state
            }
            catch(EventTimedoutException exc){
                Log.info("!!!!!!!!!!!!!! toggle time exceeded !!!!!!!!!!!!!!!!!!!!!");
            }
        }
        while(true);    
        }
        catch(ShutdownRequestException exc){
                Log.error("shutting down ... ");            
        }
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
