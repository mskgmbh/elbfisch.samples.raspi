/**
 * PROJECT   : Elbfisch - java process automation controller (jPac)
 * MODULE    : MainModule.java
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

import java.net.URISyntaxException;
import org.jpac.ImpossibleEvent;
import org.jpac.InconsistencyException;
import org.jpac.InputInterlockException;
import org.jpac.Module;
import org.jpac.OutputInterlockException;
import org.jpac.ProcessException;
import org.jpac.SignalAlreadyExistsException;
import org.jpac.WrongUseException;

/**
 *
 * @author berndschuster
 */
public class MainModule extends Module{

    public MainModule() throws SignalAlreadyExistsException, InconsistencyException, WrongUseException, URISyntaxException{
        super(null,"MainModule");
    }
    
    @Override
    protected void work() throws ProcessException {
        new ImpossibleEvent().await();
    }

    @Override
    public void start(){
        try{
            //invoke my containing modules
            new TogglePinModule(this,"TogglePinModule").start();
            new ReadBackPinModule(this,"ReadbackPinModule").start();
            //new I2cModule(this,"I2cModule").start();
            //then start myself
            super.start();
        }
        catch(Exception exc){
            Log.error("Error: ", exc);
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
