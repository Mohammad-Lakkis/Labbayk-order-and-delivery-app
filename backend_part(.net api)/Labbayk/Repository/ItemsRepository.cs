using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class ItemsRepository : IItemsRepository
    {
        private readonly DataContext _context;

        public ItemsRepository(DataContext context)
        {
            _context = context;
        }

        public ICollection<Item> GetBestFood()
        {
            return _context.Items.Where(i => i.BestFood == true).ToList();
        }

        public Item GetItem(int id)
        {
            return _context.Items.Where(i => i.Id == id).FirstOrDefault();
        }

        public Item GetItem(string title)
        {
            return _context.Items.Where(i => i.Title == title).FirstOrDefault();
        }

        public ICollection<Item> GetItemsByPrice(int priceId)
        {
            return _context.Items.OrderBy(i => i.Title).Where(i => i.PriceRange.Id == priceId).ToList();
        }

        public ICollection<Item> GetItemsByTime(int timeId)
        {
            return _context.Items.OrderBy(i => i.Title).Where(i => i.TimeRange.Id == timeId).ToList();
        }

        public ICollection<Item> GetItems()
        {
            return _context.Items.OrderBy(i => i.Id).ToList();
        }

        public ICollection<Item> GetItems(string title)
        {
            return _context.Items.OrderBy(i => i.Title).Where(i => i.Title.ToLower().Contains(title.ToLower())).ToList();
        }

        public ICollection<Item> GetItemsByCategory(string category)
        {
            return _context.Items.OrderBy(i => i.Id).Where(i => i.Category.Name == category).ToList();
        }

        public bool IsAvailable(int id)
        {
            return _context.Items.Any(i => i.Id == id);
        }

        public bool IsAvailable(string title)
        {
            return _context.Items.Any(i => i.Title.ToLower().Contains(title.ToLower()));
        }
    }
}
