package bot.boltzbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.cap.TLSCapHandler;
import org.pircbotx.dcc.ReceiveChat;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.*;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TwitchListener extends ListenerAdapter {
	public static Logger log = LoggerFactory.getLogger(TwitchListener.class);
	
	// simple chat commands
	@Override
	public void onGenericMessage(GenericMessageEvent event) {
        if (event.getMessage().startsWith("!hello boltzbot"))
                event.respond("Hello world!");
        
        if (event.getMessage().startsWith("!poem"))
        	event.respond("A poem - Roses are red, violets are blue, Xpert is not an expert...");
	}
	
	// get user list
//	@Override
//	public void onUserList(UserListEvent event) throws Exception {
//		for (User u : event.getUsers()) {
//			System.out.println(u.getNick());
//			System.out.println(u.getUserId());
//		}
//	}
	
	@Override
	public void onBoltzbotJoin(GenericChannelEvent event) {
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
        //Configure what we want our bot to do
        Configuration.Builder builder = new Configuration.Builder()
				        .setAutoNickChange(false) //Automatically change nick when the current one is in use
				        .setOnJoinWhoEnabled(false)
						.setCapEnabled(true) //Enable CAP features
						.addCapHandler(new TLSCapHandler(new UtilSSLSocketFactory().trustAllCertificates(), true))
				        .addServer("irc.chat.twitch.tv").setName("boltzmanCbot")
				        .setServerPassword("oauth:4a6np3sbfhkh2o48txibein9evzfqm") //Your oauth password from http://twitchapps.com/tmi
				        .addAutoJoinChannel("#bronain") //Some twitch channel
				        .addListener(new TwitchListener()); //Add our listener that will be called on Events
				        //.buildConfiguration();
        		
        try {
        	//Create our bot with the configuration
        	Configuration configuration = builder.buildConfiguration();
        	
        	@SuppressWarnings("resource")
            PircBotX bot = new PircBotX(configuration);
        	
        	//Connect to the server
            bot.startBot();
            
        } //In your code you should catch and handle each exception separately, but here we just lump them all together for simplicity
        catch (Exception ex) {
        	log.error("Failed to start bot", ex);
        }
        
	}

}
