package club.xiaozhe.library.strategy.book;

import club.xiaozhe.library.strategy.SQLStrategy;

class AddStrategy extends SQLStrategy {
    public AddStrategy(){
        super("INSERT INTO Books(id, name, authors, publisher, publicationDate, price, categories) VALUES(?, ?, ?, ?, ?, ?, ?)");
    }
}
