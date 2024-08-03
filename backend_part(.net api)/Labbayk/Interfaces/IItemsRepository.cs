using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface IItemsRepository
    {
        ICollection<Item> GetItems();
        ICollection<Item> GetItems(string title);
        ICollection<Item> GetBestFood();
        ICollection<Item> GetItemsByCategory(string category);
        ICollection<Item> GetItemsByTime(int timeId);
        ICollection<Item> GetItemsByPrice(int priceId);
        Item GetItem(int id);
        Item GetItem(string title);
        bool IsAvailable(int id);
        bool IsAvailable(string title);
    }
}
