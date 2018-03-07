package br.com.inventree.Storage.LocalStorage;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalStorage {

        private SharedPreferences storage;
        private SharedPreferences.Editor editor;

        public LocalStorage(Context context){
            storage = PreferenceManager.getDefaultSharedPreferences(context);
            editor = storage.edit();
        }

        public void setString(String chave, String valor){
            editor.putString(chave, valor);
            editor.apply();
        }

        public String getString(String chave){
            String value;
            value = storage.getString(chave, null);
            return value;
        }

        public void setBoolean(String chave, boolean valor){
            editor.putBoolean(chave, valor);
            editor.apply();
        }

        public boolean getBoolean(String chave){
            boolean value;
            value = storage.getBoolean(chave, false);
            return value;
        }

        public void setInt(String chave, int valor){
            editor.putInt(chave, valor);
            editor.apply();
        }

        public int getInt(String chave){
            return storage.getInt(chave, 0);
        }

        public void setLong(String chave, long valor){
            editor.putLong(chave, valor);
            editor.apply();
        }

        public long getLong(String chave){
            return storage.getLong(chave, 0);
        }

        public void deleteConstants(){
            editor.clear();
            editor.apply();
        }

        public void setDouble(String chave, double valor){
            editor.putLong(chave, Double.doubleToRawLongBits(valor));
            editor.apply();
        }

        public double getDouble(String chave){
            Double value;
            value = Double.longBitsToDouble(storage.getLong(chave, Double.doubleToLongBits(0)));
            return value;
        }

}
