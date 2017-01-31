package filtrosimagens;


import java.io.File;
import java.io.FilenameFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Décio
 */
class OnlyExt implements FilenameFilter {
  String ext;

  public OnlyExt(String ext) {
    this.ext = "." + ext;
  }

  @Override
  public boolean accept(File dir, String name) {
    return name.endsWith(ext);
  }
  
}