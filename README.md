# WARPS
The plugin has a warping system this can be used to create warps and teleport to them. Each user is, by default, allowed to create a **maximum of 3 warps**.

*Teleporting to a warp:*
> You can teleport to a warp by typing `/warp <name>`, where you replace `<name>` with the name of the warp. Example: `/warp spawn`.  
> You can see a list of available warps by using `/warp list`, and a list of your own warps by using `/warp listown`.
> You can only warp to warps that are public and/or owned by you.

*Creating a warp:*
> You can create a warp by typing `/warp create <name>`. This will create a new **public** warp at your current location with the given name.
> Warp names must be unique!

*Deleting a warp:*
> You can delete a warp by typing `/warp delete <name>`. This will delete the warp with the given name.
> You can only delete warps you have created yourself.

*Buying additional warp slots:*
> You can buy additional warp slots using `/warp buy`. Warp slots cost, by default, 500 coins for the first slot, and 550 for the second, third 600... and so on.  
> Each player starts, by default, with 3 slots.

*Making a warp public/private:*
> You can make a warp private by typing `/warp setpublic <name> false`, and you can make it public with  `/warp setpublic <name> true`.  
> When a warp is private, only you can teleport to it.
> You can only change the public status of warps you have created.
> **Warps are public by default when created!**

# TELEPORTING
The plugin has a teleport command you may use to teleport to other users.

*Teleporting to another user:*  
> You can teleport to another user using `/tp <name>`. If they have requests turned on, they will be asked to accept the teleport. If requests are turned off, the teleport will commence immediately.

*Accepting/Declining a request:*  
>You can accept a teleport request by clicking the `[Click to accept]` button in chat, or by typing `/tp accept <name>`.   To decline a request, just simply ignore it.

*Enabling/Disabling requests:*
> You can control whether others need to request before they can teleport, you do this using `/tp requirerequest <true/false>`.  
> When set to `true`, users attempting to teleport to you will require confirmation by you first, if set to `false` anyone can teleport to you without your explicit permission.
*This is **ENABLED** by default!*

*No jumpscares:*
> The player that teleports gets 5 seconds of invisibility applied to them, this way they don't just jump into your face!

# BACK
You may use the `/back` command to teleport back to the last location you teleported away from. **This does not apply to deaths!**

# WHISPERS
Whispers can be used to privately message people that are online on the server!

*Sending a whisper:*
> You can send a whisper using `/w <name> <message...>`.

*Quick reply:*
> You can quickly reply to the last whisper you sent or received using `/r <message...>`.

# NOTES
You can make a note in chat using `/note <note...>`. This puts that bit of text in the chat for only you to see. - These are **NOT** saved.

# CURRENCY
The plugin has a currency! By default, these are called coins.

*Earning Coins:*
> You earn Coins passively at a rate of 20 every 10 minutes.

*Paying other users Coins:*
> You can use `/pay` to pay other users Coins. Command usage: `/pay <name> <amount>`.

*Seeing your Coins balance:*
> You can see how much Coins you have in the player list. (Default keybind: hold TAB)

# COLORS
With the plugin you can colorize the names of items and text on signs!

*Coloring Item Names:*
> First; rename an item using an anvil while putting the color codes into the name like normal. Example: `&9Awesome Sword!`   
> Then; hold the item in your **MAIN** hand and run `/colorit`.

*Coloring Signs Text:*
> First; Place a sign while putting in the color codes into the text like normal.   
> Then; make sure your **MAIN** hand is empty, and run `/colorit`, and right click any sign to color it!

*Color codes reference:*  
![](https://i.imgur.com/fcQmiME.png)

# NICKNAMES
*Changing your nickname:*
> You can change your nickname by using `/nick <nickname>`.  
> You can also specify a color. You do this with the color codes. If you want your name to be red, then add `&c` in front of your nickname.
> Example: `/nick &cTim`

*Removing your nickname:*
> You can turn off your nickname by using `/nick off`.

# PRONOUNS
You can set your pronouns using `/pronouns <pronouns>`!  
This is simply a text field, so any combination is allowed! - Maximum of 16 characters.  
Your pronouns will be shown in the TAB list, and in your join and quit messages.

# FIREWORKS
You can launch a randomly generated firework using `/fw`! Want to ride it? Use `/fw ride` but use that at your own risk!