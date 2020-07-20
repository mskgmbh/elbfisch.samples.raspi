/**
 * PROJECT   : Elbfisch - java process automation controller (jPac)
 * MODULE    : Sample.java
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

import java.nio.charset.Charset;
import org.jpac.Module;

/**
 *
 * @author berndschuster
 */
public class Sample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
       boolean handles = false;
       System.out.println("Default Charset=" + Charset.defaultCharset());
       try{
            Module mod      = new MainModule();
            mod.start();
            Thread.sleep(2000);
            while(mod.isAlive()){Thread.sleep(100);};     
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
    }
}
