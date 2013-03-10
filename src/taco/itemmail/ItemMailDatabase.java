package taco.itemmail;

import taco.tacoapi.TacoAPI;
import taco.tacoapi.database.Database;

public class ItemMailDatabase {

	private Database data = TacoAPI.getDB();
	
	public ItemMailDatabase() {
		createTables();
	}
	
	private void createTables(){
		String sql = "CREATE TABLE IF NOT EXISTS `im_data` (`id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
				" `sender` VARCHAR(16) NOT NULL, `receiver` VARCHAR(16) NOT NULL," +
				" `item_id` INT (10) NOT NULL, `item_damage` INT (10) NOT NULL, `item_ec` VARCHAR(10) NOT NULL DEFAULT '', `item_amount` INT(10) NOT NULL," +
				" `item_name` VARCHAR(30) NOT NULL DEFAULT ''," +
				" `type` VARCHAR(10) NOT NULL, `read` INT(1) NOT NULL DEFAULT 0, `time_sent` TIMESTAMP DEFAULT CURRENT_TIMESTAMP) " +
				"ENGINE=MYISAM COMMENT='ItemMail data table'";
		data.write(sql);
	}

}
