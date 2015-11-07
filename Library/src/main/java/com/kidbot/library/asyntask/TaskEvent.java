package com.kidbot.library.asyntask;

import java.io.Serializable;

/**
 * User: YJX
 * Date: 2015-10-30
 * Time: 22:49
 */
public abstract class TaskEvent implements Serializable {
     protected abstract boolean run();
     protected abstract void success();
     protected abstract void failer();
}
