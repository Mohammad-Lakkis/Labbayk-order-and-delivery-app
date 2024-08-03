using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface IPricesRepository
    {
        ICollection<Price> GetPrices();
    }
}
