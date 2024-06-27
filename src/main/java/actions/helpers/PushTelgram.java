package actions.helpers;

import actions.utils.LogUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PushTelgram extends TelegramLongPollingBot {
	String userName_bot;
	String token_bot;
	
	public PushTelgram(String userName_bot, String token_bot) {
		this.userName_bot = userName_bot;
		this.token_bot = token_bot;
	}
	
	@Override
	public void onUpdateReceived(Update update) {
	}

	@Override
	public String getBotUsername() {
		//return "MonitorAPIRealTime_bot";
		return userName_bot;
	}

	@Override
	public String getBotToken() {
		return token_bot;
		//return "5703273375:AAFx5iEXv5WZLnJYfPS9oM3IsV5Xd6sNN3A";
	}
	
	public static boolean sendMessageText(String ChatId, String Token, String messageText) {
		TelegramBot bot = new TelegramBot(Token);
		SendResponse sendResponse = null;
		if(messageText.length() <= 4000) {
			SendMessage request = new SendMessage(ChatId, messageText).parseMode(ParseMode.HTML).disableWebPagePreview(false).disableNotification(true);
			sendResponse = bot.execute(request);
		}else {
			double soVongLap = Math.ceil((messageText.length())/4000.0);
			System.out.println("Số vòng lặp: " + soVongLap);
			int viTriCatCuoi = 4000;
			int viTriCatDau = 0;
			for(int i = 0; i < soVongLap; i++) {
				System.out.println("i= " + i +" " + messageText.substring(viTriCatDau, viTriCatCuoi));
				String catChuoi1 = messageText.substring(viTriCatDau, viTriCatCuoi);
				if(catChuoi1 != null) {
					SendMessage request = new SendMessage(ChatId, catChuoi1).parseMode(ParseMode.HTML).disableNotification(true);
					sendResponse = bot.execute(request);
				}
				viTriCatDau = viTriCatCuoi;
				if((messageText.length()-viTriCatDau) > viTriCatCuoi) {
					viTriCatCuoi += 4000;
				}else {
					viTriCatCuoi = messageText.length();
				}
			}
		}
		boolean ok = sendResponse.isOk();
		if (ok == true) {
			LogUtils.info("Send message to Telegram: " + messageText);
		} else {
			LogUtils.info("Send message to Telegram: " + ok);
		}
		return ok;
	}


}
