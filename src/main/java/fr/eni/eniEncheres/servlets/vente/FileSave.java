package fr.eni.eniEncheres.servlets.vente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class FileSave {

    public static void receiveFile(InputStream is, File dest) throws IOException {

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(dest);

            byte[] buffer = new byte[32 * 1024];

            int len;

            while ((len = is.read(buffer)) > -1)

                fos.write(buffer, 0, len);

        } catch(IOException e) {

            dest.delete();

        } finally {

            if (fos != null)

                try { fos.close(); } catch (IOException ignored) {};

            if (is != null)

                try { is.close(); } catch (IOException ignored) {};

        }

    }

}
