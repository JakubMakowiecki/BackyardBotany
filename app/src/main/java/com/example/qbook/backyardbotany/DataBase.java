package com.example.qbook.backyardbotany;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataBase
{
    String nameList[];
    int nrOfEntries;

    DBEntry retEntry;

    public void init()
    {
        FileInputStream fis = null;
        try
        {
            File file = Environment.getExternalStorageDirectory();
            File file1 = new File(file, "DBnameList.txt");
            //fis = openFileInput("DBnameList.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            this.nrOfEntries = 0;

            while ((line = br.readLine()) != null)
            {
                this.nameList[this.nrOfEntries++] = line;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

    public DBEntry search(String input)
    {
        input = input.toLowerCase();
        boolean found = false;
        DBEntry data = new DBEntry();
        for(int i = 0; i < this.nrOfEntries; i++)
        {
            if (this.nameList[i] == input)
            {
                found = true;
            }
        }
        if(found)
        {
            FileInputStream fis = null;
            try
            {
                String fileName = input + ".txt";
                //fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String line;
                int i = 0;

                while ((line = br.readLine()) != null)
                {
                    switch(i++)
                    {
                        case 0:
                            data.name = line;
                            break;
                        case 1:
                            data.info = line;
                            break;
                        case 2:
                            data.description = line;
                            break;
                        case 3:
                            data.img = line;
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (fis != null)
                {
                    try
                    {
                        fis.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        return data;
    }

}
